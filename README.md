### Earthquake - Futurice's Coding Challenge for Android Developer position

Service built that utilizes [Earthquake data from the US Government](https://earthquake.usgs.gov/earthquakes/).

This app follows Clean architecture, having 3 abstraction layers (Presentation, Domain, Data), being code organised also
in 3 sub-packages (presentation, domain, data).

Domain layer interacts with data layer through a **Repository** abstraction. This repository abstraction is implemented with 2 data sources: _memory_ and _network_ ones. _Memory_ data source caches data for 5 minutes (data refresh interval in server). _Network_ data source fetches data from server using Retrofit.
<br/>
Data layer entities are mapped into Domain layer ones using a mapper class (DomainMapper).

Presentation layer interacts with domain layer through an **Interactor** abstraction. This app contains 2 interactor implementations: get earthquakes (to list all earthquakes) and get earthquake by id (to get earthquake details).
<br/>
Presentation layer follows MVP pattern.


