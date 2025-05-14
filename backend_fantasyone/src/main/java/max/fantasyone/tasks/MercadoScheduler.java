package max.fantasyone.tasks;

import max.fantasyone.model.Liga;
import max.fantasyone.repository.LigaRepository;
import max.fantasyone.service.MercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
public class MercadoScheduler {

    private final LigaRepository ligaRepository;
    private final MercadoService mercadoService;

    @Autowired
    public MercadoScheduler(LigaRepository ligaRepository,
                            MercadoService mercadoService) {
        this.ligaRepository = ligaRepository;
        this.mercadoService = mercadoService;
    }

    //Cada día a las 8:00AM (horario de Madrid) generamos un nuevo mercado para todas las ligas
    @Scheduled(cron = "0 0 8 * * *", zone = "Europe/Madrid")
    public void rotarMercadosDiarios() {
        List<Liga> ligas = ligaRepository.findAll();
        for (Liga liga : ligas) {
            mercadoService.generarMercadoInicial(liga,10);
        }
    }
}
