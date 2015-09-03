Single Content Provider without Switch Pattern
===

Pattern to use a single ContentProvider removing the mess caused by the switches. It's still incomplete
and the code should be refactored to look better and avoid code duplication.

This "pattern" uses a main ContentProvider (BaseContentProvider) which acts as a router to route all
uri requests to the inner content providers (InnerContentProvider) to avoid messes in the provider code
and separate the code.

Classes (in com.besaba.revonline.multiplecontentproviderpattern.provider)
===
- .BaseContentProvider: Base Content Provider, Android knows only about this provider.
It's responsabile to redirect requests to the correct inner content providers.
- .InnerContentProvider: Every class that wants to act as inner provider should implement this interface.
Every method inside acts as if the class itself was a ContentProvider with the only difference that
they have an int matchId instead of an uri. \nThe method getProviderName should return the name of the
provider. It will be used to generate the uri (for example, if the method returns "blocks" the URI will be
AUTHORITY + "/" + "blocks")


Things to change/improve
===

- Use SparseArray instead of Maps?
- Refactor code
- You should manually add all the Providers in the base provider. It should be changed using Reflection
and an Annotation (maybe)
- Order in getUris is **IMPORTANT**! Use a LinkedHashMap to keep the insertion order.


Notes
===

- ContentProviders can have max 100 endpoints

