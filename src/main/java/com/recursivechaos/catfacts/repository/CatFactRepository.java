/**
 * Created by Andrew Bell 12/7/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.catfacts.repository;

import com.recursivechaos.catfacts.domain.CatFact;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: Use a real data source via properties :)
@Service
public interface CatFactRepository extends PagingAndSortingRepository<CatFact, Long> {

    List<CatFact> findByModeratedTrue();

    List<CatFact> findByModeratedFalse();
}
