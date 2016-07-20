# Dabih API

## About

This is the REST api for the next generation DABIH service by TrinnVis. It will replace the service currently
available at https://apps.trinnvis.no. The current development version connected to this backend is 
at https://app.trinnvis.no

This is a modern webapp using [Dropwizard](http://dropwizard.github.io/) and deployed to AWS with [Boxfuse](https://boxfuse.com/)

The documentation of the current build is available at https://trinnvis.no/api-docs

The latest commit is automatically built and deployed to https://app-api.trinnvis.no

NOTE: There is a release pointing to the production database deployed to https://dabih-api.trinnvis.no. A small number of endpoints are used there by the "TrinnVis Issues app" on iPhone and Android.

## Template

The content used in the api is edited in the wordpress site: https://trinnvis.no/template This produces a JSON file 
with the structure as documented in [template](TEMPALTE.md)