package pl.janksiegowy.shard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.subdomain.SubdomainController;

@SubdomainController( value= { "app"})
public class ShardController {

    @RequestMapping( value= { "", "/"})
    public ResponseEntity<?> index() {
        return ResponseEntity.ok("Hello from app.${your-domain}");
    }


}
