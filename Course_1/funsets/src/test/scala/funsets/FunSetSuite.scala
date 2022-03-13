package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */
  test("singleton set one contains one") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets:
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
  }

  test("union contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  test("intersect contains all same elements") {
    new TestSets:
      val sa = union(s1, s2)
      val sb = union(s1, s3)
      val sab = intersect(sa, sb)
      assert(contains(sab, 1), "Intersect 1")
      assert(!contains(sab, 2), "Intersect not 2")
  }

  test("diff test") {
    new TestSets:
      val sd_1 = union(s1, s2)
      val sd_2 = union(s1, s3)
      val sd_diff = diff(sd_1, sd_2)
      assert(contains(sd_diff, 2), "diff 2")
  }

  test("filter test") {
    new TestSets:
      val sa = union(s1, s2)
      val s = filter(sa, s2)
      assert(contains(s, 2), "filter 2")
  }

  test("for all") {
    new TestSets:
      val s = union(s1, s3)
      val temp = union(s1, s2)
      val f = union(s, temp)
      assert(forall(s, f), "forall")
  }

  test("exist") {
    new TestSets:
      val s = union(s1, s2)
      assert(exists(s1, s))
      assert(!exists(s3, s))
  }

  test("map") {
    new TestSets:
      val s = union(s1, s2)
      val f = map(s, x => x * x)
      assert(contains(f, 4))
      assert(contains(f, 1))
  }


  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
