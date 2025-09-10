package homework1;

public class TestedClass {
    @BeforeSuite
    public static void beforeSuiteTest() {
        System.out.println("Выполняется beforeSuiteTest");
    }

    @Test
    public void test1() {
        System.out.println("Выполняется test1");
    }

    @Test
    public void test2() {
        System.out.println("Выполняется test2");
    }

    @Test(priority = 10)
    public void test3() {
        System.out.println("Выполняется test3");
    }

//    @Test(priority = 11)
    public void test4() {
        System.out.println("Выполняется test4");
    }

    @Test(priority = 1)
    public void test5() {
        System.out.println("Выполняется test5");
    }

    @AfterSuite
    public static void afterSuiteTest() {
        System.out.println("Выполняется afterSuiteTest");
    }

//    @AfterSuite
    public void afterSuiteTest2() {
        System.out.println("Выполняется afterSuiteTest2");
    }
}
