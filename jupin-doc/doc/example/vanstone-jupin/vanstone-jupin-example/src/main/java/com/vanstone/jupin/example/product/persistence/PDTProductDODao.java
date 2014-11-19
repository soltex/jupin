/**
 * 
 */
package com.vanstone.jupin.example.product.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.vanstone.jupin.example.product.persistence.object.PDTProductDO;
import com.vanstone.mongo.AbstractBaseMongoDAO;

/**
 * @author shipeng
 */
@Repository
public class PDTProductDODao extends AbstractBaseMongoDAO<PDTProductDO> {

	@Autowired
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
}
