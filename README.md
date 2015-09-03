Single Content Provider without Switch Pattern
===

Pattern to use a single ContentProvider removing the mess caused by the switches. It's still incomplete
and the code should be refactored to look better and avoid code duplication.

This "pattern" uses a main ContentProvider (BaseContentProvider) which acts as a router to route all
uri requests to the inner content providers (InnerContentProvider) to avoid messes in the provider code
and separate the code.

Things to change/improve:

- Use SparseArray instead of Maps?
- Refactor code

Notes:

- ContentProviders can have max 100 endpoints
