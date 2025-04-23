package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {

    private final RestTemplateBuilder restTemplateBuilder;

    public static final String GET_BEER_PATH = "/api/v1/beer";
    public static final String GET_BEER_BY_ID_PATH = "/api/v1/beer/{beerId}";

    @Override
    public void deleteBeer(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.delete(GET_BEER_BY_ID_PATH, beerId);
    }

    @Override
    public BeerDTO updateBeer(BeerDTO beerDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.put(GET_BEER_BY_ID_PATH, beerDto, beerDto.getId());
        return getBeerById(beerDto.getId());
    }

    @Override
    public BeerDTO createBeer(BeerDTO newDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        URI uri = restTemplate.postForLocation(GET_BEER_PATH, newDto);
        return restTemplate.getForObject(uri.getPath(), BeerDTO.class);
    }

    @Override
    public BeerDTO getBeerById(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.getForObject(GET_BEER_BY_ID_PATH, BeerDTO.class, beerId);
    }

    @Override
    public Page<BeerDTO> listBeers() {
        return this.listBeers(null, null, null, null, null);
    }

    @Override
    public Page<BeerDTO> listBeers(String beerName,BeerStyle beerStyle, Boolean showInventory, Integer pageNumber,
                                   Integer pageSize) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH);

        if (beerName != null) {
            uriComponentsBuilder.queryParam("beerName", beerName);
        }

        if (beerStyle != null) {
            uriComponentsBuilder.queryParam("beerStyle", beerStyle);
        }

        if (showInventory != null) {
            uriComponentsBuilder.queryParam("showInventory", beerStyle);
        }

        if (pageNumber != null) {
            uriComponentsBuilder.queryParam("pageNumber", beerStyle);
        }

        if (pageSize != null) {
            uriComponentsBuilder.queryParam("pageSize", beerStyle);
        }


        ResponseEntity<BeerDTOPageImpl> response =
                restTemplate.getForEntity(uriComponentsBuilder.toUriString() , BeerDTOPageImpl.class);


        return response.getBody();
    }
}
