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
