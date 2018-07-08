package service;

import com.yangjianzhou.util.BootUnitilsJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.unitils.database.annotations.Transactional;
import org.unitils.database.util.TransactionMode;
import org.unitils.spring.annotation.SpringApplicationContext;

/**
 * Created by yangjianzhou on 16-4-16.
 */

/*@Transactional(value = TransactionMode.ROLLBACK)
@SpringApplicationContext({"classpath:applicationContext-test.xml", "classpath:dataSource-test.xml"})
@TestExecutionListeners({SpringUnitilsAdaptorTestExecutionListener.class})*/

//@TestExecutionListeners({SpringUnitilsAdaptorTestExecutionListener.class})
//@SpringApplicationContext({"classpath:applicationContext-test.xml", "classpath:dataSource-test.xml"})
@Transactional(value = TransactionMode.ROLLBACK)
@RunWith(BootUnitilsJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml", "classpath:dataSource-test.xml"})
public abstract class BaseServiceTest /*extends UnitilsJUnit4*/ {
}
