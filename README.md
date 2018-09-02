# Nice Gallery


## Architecture

![Clean architecture](https://i.imgur.com/LazlarG.png)

*Android part* contains the view. *Business part* contains functionals rules. It doesn't use any library nor android objects. *Repository part* contains API calls and *in memory cache*.
*Android part* and *Repository part* target *Business part*.

There is some differences with *big architecture*: 

- No Controller: in this case, it would have only transfered the request to the Interactor.
- No class or function only for the thread/coroutine change

## Other

I chose to not implement these parts to be faster in my development.
All the classes (except view) are tested by unit tests. All the code is in Kotlin.
