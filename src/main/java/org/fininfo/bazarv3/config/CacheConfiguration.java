package org.fininfo.bazarv3.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, org.fininfo.bazarv3.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, org.fininfo.bazarv3.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, org.fininfo.bazarv3.domain.User.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.Authority.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.User.class.getName() + ".authorities");
            createCache(cm, org.fininfo.bazarv3.domain.Categorie.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.Categorie.class.getName() + ".sousCategories");
            createCache(cm, org.fininfo.bazarv3.domain.Categorie.class.getName() + ".stocks");
            createCache(cm, org.fininfo.bazarv3.domain.Categorie.class.getName() + ".images");
            createCache(cm, org.fininfo.bazarv3.domain.SousCategorie.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.SousCategorie.class.getName() + ".produits");
            createCache(cm, org.fininfo.bazarv3.domain.SousCategorie.class.getName() + ".stocks");
            createCache(cm, org.fininfo.bazarv3.domain.SousCategorie.class.getName() + ".images");
            createCache(cm, org.fininfo.bazarv3.domain.Produit.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.Produit.class.getName() + ".stocks");
            createCache(cm, org.fininfo.bazarv3.domain.Produit.class.getName() + ".mouvementStocks");
            createCache(cm, org.fininfo.bazarv3.domain.Produit.class.getName() + ".commandeLignes");
            createCache(cm, org.fininfo.bazarv3.domain.Produit.class.getName() + ".remises");
            createCache(cm, org.fininfo.bazarv3.domain.Produit.class.getName() + ".images");
            createCache(cm, org.fininfo.bazarv3.domain.ProdEnumeration.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.ProduitUnite.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.Stock.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.MouvementStock.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.Client.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.Client.class.getName() + ".adresses");
            createCache(cm, org.fininfo.bazarv3.domain.Client.class.getName() + ".commandes");
            createCache(cm, org.fininfo.bazarv3.domain.Adresse.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.Adresse.class.getName() + ".clients");
            createCache(cm, org.fininfo.bazarv3.domain.Adresse.class.getName() + ".commandes");
            createCache(cm, org.fininfo.bazarv3.domain.Commande.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.Commande.class.getName() + ".mouvementStocks");
            createCache(cm, org.fininfo.bazarv3.domain.Commande.class.getName() + ".commandeLignes");
            createCache(cm, org.fininfo.bazarv3.domain.CommandeLignes.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.Zone.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.Zone.class.getName() + ".adresses");
            createCache(cm, org.fininfo.bazarv3.domain.Zone.class.getName() + ".livraisons");
            createCache(cm, org.fininfo.bazarv3.domain.Zone.class.getName() + ".codePostauxes");
            createCache(cm, org.fininfo.bazarv3.domain.Livraison.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.CodePostaux.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.CodePostaux.class.getName() + ".adresses");
            createCache(cm, org.fininfo.bazarv3.domain.Images.class.getName());
            createCache(cm, org.fininfo.bazarv3.domain.Remise.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
