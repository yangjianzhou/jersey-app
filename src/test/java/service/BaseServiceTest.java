package service;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.unitils.database.annotations.Transactional;
import org.unitils.database.util.TransactionMode;

/**
 * Created by yangjianzhou on 16-4-16.
 */

@Transactional(value= TransactionMode.COMMIT)
@ContextConfiguration({"classpath:applicationContext-test.xml" , "classpath:dataSource-test.xml"})
public abstract class BaseServiceTest extends AbstractJUnit4SpringContextTests{
}
