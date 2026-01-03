package guru.springframework.spring7resttemplate.client;

import guru.springframework.spring7resttemplate.model.BeerDTO;
import org.springframework.data.domain.Page;

/**
 * Created by jt, Spring Framework Guru.
 */
public class BeerClientImpl implements BeerClient {
    @Override
    public Page<BeerDTO> listBeers() {
        return null;
    }
}
