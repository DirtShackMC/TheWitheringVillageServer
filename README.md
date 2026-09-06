# The Withering Village

A Paper plugin with a small command router and player-specific waypoint system.

## Commands

The root command is `/village`, declared in `plugin.yml`.

```text
/village                         Show registered subcommands
/village waypoint <x> <y> <z>    Place a waypoint in your current world
```

Coordinates are currently whole numbers, and the waypoint command can only be
run by a player. Typing `/village way...` tab-completes registered subcommand
names. Unknown commands show the help list; invalid arguments show usage.

### How command dispatch works

1. `TheWitheringVillage#onEnable` creates a `CommandManager` for `/village`.
2. Each command implementing `Subcommand` is registered by name.
3. `CommandManager` removes the first argument, then passes the rest to the
   matching subcommand's `execute` method.
4. Returning `false` from `execute` makes the manager print that command's
   usage. Each subcommand may also provide its own tab completions.

To add another command, implement `Subcommand` and register one instance in
`TheWitheringVillage#onEnable`.

## Waypoints

`WaypointManager` stores waypoints by player UUID and case-insensitive name:

```text
player UUID -> waypoint name -> WaypointDisplay
```

Calling `show(name, player, location, text)` spawns three non-persistent
`TextDisplay` entities:

- a green diamond marker;
- a gold outline;
- optional text above the marker.

The displays face the camera, render at full brightness through blocks, and
are hidden by default. Only the target player is shown them, so waypoints are
private even though their entities exist in the world.

`TaskManager` uses the same system for delivery objectives: assigning a task
replaces the player's waypoint with a chest marker and progress text such as
`IRON_INGOT: 0/10`.

Waypoints currently live only in memory. They are removed when a player quits
and cleared when the plugin disables; they are not restored after reconnects
or server restarts. Named removal, text updates, and delivery progress handling
are still unfinished.

## Hackathon development time

Approximately **4 hours 30 minutes** have been spent on the project so far:

| Work | Time |
| --- | ---: |
| Initial project setup | 30 minutes |
| Waypoint manager and waypoint system | 1 hour |
| Command system | 1 hour |
| Integrating the systems | 1 hour |
| Task manager (incomplete) | 1 hour |
