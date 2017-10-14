package service;

import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.Transactional;
import org.unitils.database.util.TransactionMode;
import org.unitils.spring.annotation.SpringApplicationContext;

/**
 * Created by yangjianzhou on 16-4-16.
 */

@Transactional(value = TransactionMode.ROLLBACK)
@SpringApplicationContext({"classpath:applicationContext-test.xml", "classpath:dataSource-test.xml"})
public abstract class BaseServiceTest extends UnitilsJUnit4 {
}
