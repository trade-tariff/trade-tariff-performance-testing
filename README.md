# trade-tariff-performance-testing
Performance testing setup for Trade Tariff

# Getting Started

Install Docker
--------------

See [Docker installation instructions](https://docs.docker.com/engine/install/)

Start SBT Shell (optional)
---------

```bash
$ ./sbt shell
```

Run all simulations
-------------------

From with SBT

```bash
> gatling:test

```

or from your shell

```
$ ./sbt
```

Run a single simulation (e.g SectionsSimulation)
-----------------------

From within SBT

```bash
> gatling:testOnly tradetariff.SectionsSimulation
```

or from your shell

```
$ ./sbt "gatling:testOnly tradetariff.SectionsSimulation"
```

Setting test URL
------------

By default the app will use the staging url via the CDN.

Alternative URLs can be used by setting the `PERFTESTURL` env var

# Simulations

## tradetariff.SectionsSimultation

In this simulation a VU (Virtual User) does the following steps:
- Gets the Sections index at /sections
- Requests a random Section /sections/[1-21]
- Requests a random chapter in the previously selected section


## tradetariff.CommoditiesSimultation

In this simulation a VU (Virtual User) does the following steps:
- Requests a random commodity from the `commodities.csv` list
