package no.plasmid.blopp;

import javax.servlet.Filter;

import no.plasmid.blopp.orientdb.OrientDBTransactionFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class WebConfig {

  @Bean
  @Order(value=2)
  public Filter orientDBTransactionFilter() {
      return new OrientDBTransactionFilter();
  }

  @Bean
  @Order(value=3)
  public Filter urlResolverFilter() {
      return new URLResolverFilter();
  }

}
