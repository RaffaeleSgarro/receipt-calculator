Shopping Cart Billing
=====================

Billing of the shopping cart needs to be done applying the discounts on each discounted item. 
Discounts are determined by the type of the item: Grocery items get 7.5% discount, Books get 12% discount. If the total bill amount exceeds 40 euros 
additional 5% discount is applied on overall total.

Bill should display unit price, discount %, final price and final total and total discount. Final total 
should be rounded off to nearest 5 cents. Price per unit is given.

I implemented the solution as a set of test cases in `EndToEndTest.java`

Run with

    .\gradlew test
