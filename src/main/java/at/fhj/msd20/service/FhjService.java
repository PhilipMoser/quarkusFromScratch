package at.fhj.msd20.service;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FhjService {

    public String sayHallo() {
        return "hello wold! :)";
    }
}
