package org.mourya.msscbeerservice.config;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.mourya.brewery.model.BeerDto;
import org.mourya.brewery.model.BeerPagedList;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;
import java.util.UUID;

@Configuration
@EnableCaching
public class EhcacheConfig {
    @Bean
    public CacheManager EhcacheManager() {

        CacheConfiguration<UUID, BeerDto> cachecConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(UUID.class,
                        BeerDto.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(1, MemoryUnit.MB)
                                .build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(120)))
                .build();

        CacheConfiguration<String, BeerDto> cachecConfigForUpc = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class,
                        BeerDto.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(1, MemoryUnit.MB)
                                .build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(120)))
                .build();

        CacheConfiguration<Object, BeerPagedList> cachecConfigForList = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Object.class,
                    BeerPagedList.class,
        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(1, MemoryUnit.MB)
                                .build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(120)))
                .build();

        return getCacheManager(cachecConfig, cachecConfigForUpc, cachecConfigForList);
    }

    private static CacheManager getCacheManager(CacheConfiguration<UUID, BeerDto> cachecConfig, CacheConfiguration<String, BeerDto> cachecConfigForUpc, CacheConfiguration<Object, BeerPagedList> cachecConfigForList) {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();


        javax.cache.configuration.Configuration<UUID, BeerDto> configuration = Eh107Configuration.fromEhcacheCacheConfiguration(cachecConfig);
        javax.cache.configuration.Configuration<String, BeerDto> configurationForUpc = Eh107Configuration.fromEhcacheCacheConfiguration(cachecConfigForUpc);
        javax.cache.configuration.Configuration<Object, BeerPagedList> configurationForList = Eh107Configuration.fromEhcacheCacheConfiguration(cachecConfigForList);
        cacheManager.createCache("beerCache", configuration);
        cacheManager.createCache("beerListCache", configurationForList);
        cacheManager.createCache("beerUpcCache", configurationForUpc);
        return cacheManager;
    }
}
