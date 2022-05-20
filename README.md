# “Comparison method violates its general contract!”

Here a couple of ways to find the origin of this issue.

## 1. Finding the error in your Comparator’s compare method aka “Comparison method violates its general contract!”

If you google the term ‘Comparison method violates its general contract’, you will find some articles on [stackoverflow](http://stackoverflow.com/questions/11441666/java-error-comparison-method-violates-its-general-contract) that sort of help. But these didn’t help me because like Forrest Gump, “I’m not a smart man but I know what love is”.

So anyways, I had a list of things and the call to Collections.sort was breaking which this error. After reading the replies on stackoverflow, I knew why it was breaking, but I still didn’t know why it was breaking for me and in my specific case. What I needed was the exact case which broke the transitivity contract. And my list had over [fiddy](http://www.urbandictionary.com/define.php?term=fiddy) items. 

**Transitivity of a [Comparator](http://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html) means that when comparing 3 items (A, B, C), if A > B and then B > C then it must be that A > C.**

**So if you get this exception it means that you are breaking the above contract**

One hidden thing to note is that because the Java Sorting function is stable. Stable meaning that: equal elements will not be reordered as a result of the sort. So this exception may happen with a different ordering of the same list of items. Therefore one crucial thing you have to do is to be able to reproduce the exception with a list of items.

Here is what you can do to solve this issue on a production system:

1. Recreate the problem
2. Modify the code, so that just before the sort, you dump the whole list into a file (via serialization)
3. Write new code to read this file, and re-create the list
4. Run the list into a Sort to verify the problem exists
5. Modify the comparator to satisfy the Java Gods
6. ???
7. Profit

 Given that a comparator might be breaking the the transitivity contract, and we know what the rules of transitivity are, what I needed to know was which of the 3 items on my list were breaking the comparator and why. And so here is some code that will iterate over a list and do the following:

1. Select (the next) 3 items from the list
2. check the 3 items for the transitivity
3. go to step 1

Since order matters, we have to select every 3 item combination from this list. So that is what the following bit of code does. It helps to identify the 3 items that are breaking the contact of transitivity. Then using just those 3 items, you can debug your comparator and finally modify it to appease the Java Gods. Here is the code to find those 3 are in the class `TestComparatorForTransitivity`

## 2. Multithreading issues

So you have checked your comparator and you believe that it is transitive but
you are still getting this exception?

Another possible cause for this can be that you have several threads sorting
your list at the same time as illustrated in the `ContractViolator` class. 

This code will generate a `java.lang.IllegalArgumentException: Comparison method violates its general contract!` along some other `java.util.ConcurrentModificationException`.

To solve this, you then have to make sure that only one thread accesses your 
list at one time, either by synchronizing or working with copies.