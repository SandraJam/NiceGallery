# Nice Gallery


## Architecture

![Clean architecture](https://imgur.com/a/uEh4XZp)

*Android part* contains the view. *Business part* contains functionals rules. It does used no library and no android object. *Repository part* contains API calls and *in memory cache*.
*Android part* and *Repository part* target *Business part*.

There is some differences with *big architecture*: 

- No Controller: in this case, it's only boilerplate.
- No class or function only for the thread/coroutine change

## Other

I choose to don't implement this parts to be faster in my development.
All the classes (except view) are tested by unit tests. All the code is in Kotlin.
