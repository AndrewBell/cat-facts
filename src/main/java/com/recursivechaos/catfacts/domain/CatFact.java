/**
 * Created by Andrew Bell 12/7/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.catfacts.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class CatFact {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String fact;

    public CatFact(String fact){
        this.fact = fact;
    }

}
