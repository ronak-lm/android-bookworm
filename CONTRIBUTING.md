# Contributing

If you wish to contribute to this project, feel free to [fork Book Worm][1], make changes and submit a [pull request][2]. Make sure to keep the commit style consistent.


## Setup

To work, the project will need API keys from [New York Times Bestseller API][3] and [Google Books API][4]. Create a file called "keys.xml" in the \res\values folder with these values:-

	<string name="nyt_bestseller_key">YOUR_NYTB_KEY_HERE</string>
    <string name="google_books_key">YOUR_GOOGLE_BOOKS_KEY_HERE</string>


## Signing Release Builds

You will have to add your keystore file with the name "keystore.jks" in the app folder. Create a "keystore.properties" file in the the project's root folder with these values:-

	storeFile=keystore.jks
	keyAlias=YOUR_KEY_ALIAS_HERE
	storePassword=YOUR_STORE_PASS_HERE
	keyPassword=YOUR_KEY_PASS_HERE

 [1]: https://github.com/Ronak-LM/book-worm/fork
 [2]: https://github.com/Ronak-LM/book-worm/compare
 [3]: http://developer.nytimes.com/
 [4]: https://developers.google.com/books/