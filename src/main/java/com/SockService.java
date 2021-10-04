package com;

import com.models.Color;
import com.models.CottonPart;
import com.models.Sock;
import com.repository.ColorRepository;
import com.repository.CottonPartRepository;
import com.repository.SockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SockService {
    @Autowired
    SockRepository sockRepository;
    @Autowired
    CottonPartRepository cottonPartRepository;
    @Autowired
    ColorRepository colorRepository;

    public Color findColorOrSave(String color) {
        color = color.trim();
        Optional<Color> colorToDb =this.colorRepository.findOneByColor(color);
        if(colorToDb.isEmpty())
            return this.colorRepository.save(new Color(color));
        else return colorToDb.get();
    }

    public CottonPart findCottonPartOrSave(int cottonPart) {
        Optional<CottonPart> cpToDb = this.cottonPartRepository.findOneByCottonPart(cottonPart);
        if(cpToDb.isEmpty())
            return this.cottonPartRepository.save(new CottonPart(cottonPart));
        else return cpToDb.get();
    }

    public boolean income(Color color, CottonPart cottonPart, int quantity) {
        Optional<Sock> Sock = this.sockRepository.findOneByColorAndCottonPart(color, cottonPart);
        if (Sock.isPresent()) {
            Sock.get().incomeSocks(quantity);
            this.sockRepository.save(Sock.get());
        } else {
            this.sockRepository.save(new Sock(color, quantity, cottonPart));
        }
        return true;
    }
    public boolean outcome(Color color, CottonPart cottonPart, int quantity) {
        Optional<Sock> sockData = this.sockRepository.findOneByColorAndCottonPart(color, cottonPart);
        if (sockData.isPresent()) {
            Sock Sock = sockData.get();
            Sock.outcomeSocks(quantity);
            if (Sock.getQuantity() >= 0) {
                this.sockRepository.save(Sock);
                return true;
            }
        }
        return false;
    }

    public String countWithCondition(Color color, CottonPart cottonPart, String operation) {

    return new String();
    }
}
