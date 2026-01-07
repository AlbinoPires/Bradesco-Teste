package sd.clienteservidor.bradescoteste.service;

import java.util.List;

public interface IService<REQ, RES, ITEM> {

    RES pagar(REQ dto);
    List<ITEM> listar();

}
