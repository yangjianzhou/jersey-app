package service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.Transactional;
import org.unitils.database.util.TransactionMode;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.util.SpringUnitilsAdaptorTestExecutionListener;

/**
 * Created by yangjianzhou on 16-4-16.
 */

/*@Transactional(value = TransactionMode.ROLLBACK)
@SpringApplicationContext({"classpath:applicationContext-test.xml", "classpath:dataSource-test.xml"})
@TestExecutionListeners({SpringUnitilsAdaptorTestExecutionListener.class})*/

//@ContextConfiguration({"classpath:applicationContext-test.xml", "classpath:dataSource-test.xml"})
//@TestExecutionListeners({SpringUnitilsAdaptorTestExecutionListener.class})
@SpringApplicationContext({"classpath:applicationContext-test.xml", "classpath:dataSource-test.xml"})
@Transactional(value = TransactionMode.ROLLBACK)
//@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseServiceTest extends UnitilsJUnit4 {
}
