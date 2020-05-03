package edu.wctc.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

// An extension of Jackson's ObjectMapper that configures
// itself to use Hibernate 5
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {
        // Our object mapper is just like Jackson's default,
        // but it knows it's using Hibernate 5
        registerModule(new Hibernate5Module());

        // This is supposed to stop LocalDates from printing as
        // giant objects, but it's not working *sigh*
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

}
