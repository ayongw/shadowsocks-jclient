# event process
## start server
## add selectEvent(loopEvent)
## process selectEvent
    this will produce acceptEvent and readEvent
## process acceptEvent
    accept client connect and
    register OP_READ event to selector,

## process ReadEvent
    read client data
    and register WriteEvent(write data to opposite channel)
