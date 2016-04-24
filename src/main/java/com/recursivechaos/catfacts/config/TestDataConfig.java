/**
 * Created by Andrew Bell 12/7/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.catfacts.config;

import com.recursivechaos.catfacts.domain.CatFact;
import com.recursivechaos.catfacts.repository.CatFactRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestDataConfig {

    @Bean
    public CommandLineRunner start(CatFactRepository repository) {
        return (args) -> {
            repository.save(new CatFact("Cat's are jerks.", true));
            repository.save(new CatFact("Cat's only know how to kill.", true));
            repository.save(new CatFact("A cat will eat you. I read it on the internet once.", true));
        };
    }

}
