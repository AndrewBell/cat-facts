/**
 * Created by Andrew Bell 12/7/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.catfacts.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class CatFact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fact;
    @JsonIgnore
    private boolean moderated;

    // Consider hiding moderated field once not manually creating data
    public CatFact(String fact, boolean moderated) {
        this.fact = fact;
        this.moderated = moderated;
    }

    public CatFact(String fact) {
        this.fact = fact;
    }
}
