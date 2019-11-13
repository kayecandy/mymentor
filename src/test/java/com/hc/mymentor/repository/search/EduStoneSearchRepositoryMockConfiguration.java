package com.hc.mymentor.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link EduStoneSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class EduStoneSearchRepositoryMockConfiguration {

    @MockBean
    private EduStoneSearchRepository mockEduStoneSearchRepository;

}
