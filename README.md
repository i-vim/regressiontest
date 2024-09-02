# Showcase to demonstrate possible regression in 3.14.1

Have a look at `RegressionTest.kt`. We expect the bean to contain the value set in the test profile. Instead, it carries the value coming from the lifecycle manager.
