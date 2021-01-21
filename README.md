# trade-tariff-performance-testing
Performance testing setup for Trade Tariff 

# Getting Started

Start SBT
---------
```bash
$ sbt
```

Run all simulations
-------------------

```bash
> gatling:test
```
or 
```
sbt gatling:test
```

Run a single simulation (e.g SectionsSimulation)
-----------------------

```bash
> gatling:testOnly tradetariff.SectionsSimulation
```
or 
```
sbt "gatling:testOnly tradetariff.SectionsSimulation"
```




# Simulations

## tradetariff.SectionsSimultation

In this simulation a VU (Virtual User) does the following steps:
- Gets the Sections index at /sections
- Requests a random Section /sections/[1-21]
- Requests a random chapter in the previously selected section


## tradetariff.CommoditiesSimultation

In this simulation a VU (Virtual User) does the following steps:
- Requests a random commodity from the `commodities.csv` list
