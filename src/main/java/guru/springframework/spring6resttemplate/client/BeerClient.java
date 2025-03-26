package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import org.springframework.data.domain.Page;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface BeerClient {

    Page<BeerDTO> listBeers(String beerName);
}
