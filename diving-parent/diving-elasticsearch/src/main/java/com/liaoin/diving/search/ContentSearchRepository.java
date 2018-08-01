package com.liaoin.diving.search;

import com.liaoin.diving.entity.Content;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ContentSearchRepository extends ElasticsearchRepository<Content, Integer> {

}
