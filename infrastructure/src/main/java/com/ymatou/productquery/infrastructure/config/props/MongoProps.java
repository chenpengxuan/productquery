package com.ymatou.productquery.infrastructure.config.props;

/**
 * Created by zhangyifan on 2016/12/09.
 */

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.stereotype.Component;

@Component
@DisconfFile(fileName = "mongo.properties")
public class MongoProps {
    private String mongoProductUrl;
    private String mongoHistoryProductUrl;

    @DisconfFileItem(name = "mongoProductUri")
    public String getMongoProductUrl() {
        return mongoProductUrl;
    }

    public void setMongoProductUrl(String mongoProductUrl) {
        this.mongoProductUrl = mongoProductUrl;
    }

    @DisconfFileItem(name = "mongoHistoryProductUri")
    public String getMongoHistoryProductUrl() {
        return mongoHistoryProductUrl;
    }

    public void setMongoHistoryProductUrl(String mongoHistoryProductUrl) {
        this.mongoHistoryProductUrl = mongoHistoryProductUrl;
    }
}
