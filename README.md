## Theoretical Questions

1. Interrupting a thread while sleeping results in InterruptedException. So, catch and finally blocks will be executed. "Thread was interrupted!" and "Thread will be finished here!!!" will be printed on screen.

2. There's no thread defined in main method. The code will run in main thread and result is main on screen.

3. First print statement is defined in the task which extends Thread class and will be run in Thread_0. The second print will run in main thread.