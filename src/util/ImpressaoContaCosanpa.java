package util;

import business.ControladorRota;
import model.Imovel;


public class ImpressaoContaCosanpa {

    private static ImpressaoContaCosanpa instancia;
    private static Imovel imovel;
    public static String comandoCPCL = "! 0 200 200 1720 1\n"+
    		"BOX 32 435 802 482 1\n"+
    		"LINE 720 415 720 455 1\n"+
    		"LINE 403 415 403 477 1\n"+
    		"BOX 32 411 802 435 1\n"+
    		"LINE 278 415 278 477 1\n"+
    		"BOX 283 518 802 545 1\n"+
    		"BOX 283 545 802 692 1\n"+
    		"LINE 656 518 656 692 1\n"+
    		"LINE 425 518 425 692 1\n"+
    		"LINE 535 518 535 692 1\n"+
    		"T 0 2 135 121 Versao: 4.2.1.1 - 21/07/2012 18:47:39 /2\n"+
    		"T 7 1 464 90 6867073\n"+
    		"T 7 1 669 90 Julho/2012\n"+
    		"T 0 0 201 47 04.945.341/0001-90\n"+
    		"T 0 0 285 64 00000000000150749988\n"+
    		"T 0 0 222 81 212\n"+
    		"T 0 0 140 108 \n"+
    		"T 0 2 52 172 MANOELLA CARNEIRO GONCALVES\n"+
    		"T 0 2 52 199 \n"+
    		"T 0 2 434 169 AV VISCONDE DE SOUZA FRANCO, NUMERO 601\n"+
    		"T 0 2 434 196 - APTO-3102 - UMARIZAL BELEM P\n"+
    		"T 7 0 15 250 001.003.1807.0028.062\n"+
    		"T 7 0 315 250 18\n"+
    		"T 7 0 415 250 28\n"+
    		"T 0 0 470 254 RESIDENC\n"+
    		"T 7 0 539 250 1\n"+
    		"T 7 0 48 301 C09L000443\n"+
    		"T 7 0 248 301 11/04/2011\n"+
    		"T 7 0 446 301 LIGADO\n"+
    		"T 7 0 627 301 LIGADO\n"+
    		"T 7 0 168 330 LEITURA\n"+
    		"T 7 0 190 354 259\n"+
    		"T 7 0 190 378 260\n"+
    		"T 7 0 313 330 DATA\n"+
    		"T 7 0 285 354 22/06/2012\n"+
    		"T 7 0 285 378 21/07/2012\n"+
    		"T 7 0 37 354 ANTERIOR\n"+
    		"T 7 0 37 378 ATUAL\n"+
    		"T 7 0 163 412 FATURADO\n"+
    		"T 7 0 190 436 259\n"+
    		"T 7 0 190 460 260\n"+
    		"T 7 0 313 412 DATA\n"+
    		"T 7 0 285 436 22/06/2012\n"+
    		"T 7 0 285 460 21/07/2012\n"+
    		"T 7 0 418 412 CONSUMO (m3)\n"+
    		"T 7 0 511 436 1\n"+
    		"T 7 0 745 412 DIAS\n"+
    		"T 7 0 760 436 31\n"+
    		"T 7 0 37 436 ANTERIOR\n"+
    		"T 7 0 37 460 ATUAL\n"+
    		"T 7 0 50 499 ULTIMOS CONSUMOS\n"+
    		"LINE 115 525 115 665 1\n"+
    		"T 0 2 44 522 2012/06\n"+
    		"T 0 2 127 522 5 m3 \n"+
    		"T 0 2 44 547 2012/05\n"+
    		"T 0 2 127 547 2 m3 \n"+
    		"T 0 2 44 572 2012/04\n"+
    		"T 0 2 127 572 5 m3 \n"+
    		"T 0 2 44 597 2012/03\n"+
    		"T 0 2 127 597 6 m3 \n"+
    		"T 0 2 44 622 2012/02\n"+
    		"T 0 2 127 622 5 m3 \n"+
    		"T 0 2 44 647 2012/01\n"+
    		"T 0 2 127 647 4 m3 \n"+
    		"T 7 0 75 672 MEDIA(m3):\n"+
    		"T 7 0 195 672 4\n"+
    		"T 7 0 448 496 QUALIDADE DA AGUA\n"+
    		"T 0 0 672 505 Ref: \n"+
    		"T 0 0 705 505 Julho/2012\n"+
    		"T 7 0 287 520 PARAMETROS\n"+
    		"T 7 0 428 520 PORT. 518\n"+
    		"T 7 0 540 520 ANALISADO\n"+
    		"T 7 0 672 520 CONFORME\n"+
    		"T 0 0 287 552 COR(uH)\n"+
    		"T 0 0 287 571 TURBIDEZ(UT)\n"+
    		"T 0 0 287 590 CLORO(mg/L)\n"+
    		"T 0 0 287 609 FLUOR(mg/L)\n"+
    		"T 0 0 287 628 COLIFORME TOTAL\n"+
    		"T 0 0 287 640 Pres/Aus)\n"+
    		"T 0 0 287 657 COLIFORME TERMO\n"+
    		"T 0 0 287 671 TOLER.(Pres/Aus)\n"+
    		"T 0 0 469 552 71\n"+
    		"T 0 0 469 571 71\n"+
    		"T 0 0 469 590 258\n"+
    		"T 0 0 469 609 35\n"+
    		"T 0 0 469 628 258\n"+
    		"T 0 0 469 657 258\n"+
    		"T 0 0 582 552 78\n"+
    		"T 0 0 582 571 78\n"+
    		"T 0 0 582 590 78\n"+
    		"T 0 0 582 628 78\n"+
    		"T 0 0 582 657 78\n"+
    		"T 0 0 726 552 76\n"+
    		"T 0 0 726 571 77\n"+
    		"T 0 0 726 609 0\n"+
    		"T 0 0 726 628 77\n"+
    		"T 0 0 726 657 78\n"+
    		"T 7 0 53 708 DESCRICAO\n"+
    		"T 7 0 571 708 CONSUMO\n"+
    		"T 7 0 687 708 TOTAL(R$)\n"+
    		"T 7 0 53 733 AGUA\n"+
    		"T 7 0 63 767 RESIDENCIAL 1 UNIDADE(S)\n"+
    		"T 7 0 73 801 CONSUMO DE AGUA\n"+
    		"T 7 0 571 801 1 m3\n"+
    		"T 7 0 697 801 14,00\n"+
    		"T 7 0 53 835 ESGOTO 60,00 % DO VALOR DE ?GUA\n"+
    		"T 7 0 697 835 8,40\n"+
    		"T 7 0 53 869 RATEIO DE ?GUA DO CONDOM?NIO\n"+
    		"T 7 0 697 869 23,65\n"+
    		"T 7 0 53 903 RATEIO DE ESGOTO DO CONDOM?NIO\n"+
    		"T 7 0 697 903 14,19\n"+
    		"T 7 1 160 1210 14/08/2012\n"+
    		"T 4 0 640 1210 60,24\n"+
    		"T 0 2 424 1265 OPCAO PELO DEB. AUTOMATICO: \n"+
    		"T 5 0 649 1266 6867073\n"+
    		"T 0 3 35 1300 \n"+
    		"T 0 3 35 1330 \n"+
    		"T 0 3 35 1360 \n"+
    		"T 0 2 344 1456 6867073\n"+
    		"T 0 2 443 1456 07/2012\n"+
    		"T 0 2 558 1456 14/08/2012\n"+
    		"T 0 2 694 1456 60,24\n"+
    		"T 5 0 66 1515 82670000000-1 60240022001-1 06867073000-8 07201280003-2\n"+
    		"B I2OF5 1 2 90 35 1538 82670000000602400220010686707300007201280003\n"+
    		"T 5 0 109 1661 212\n"+
    		"T 5 0 352 1661 4\n"+
    		"FORM\n"+
    		"PRINT\n";

    

    protected static ImpressaoContaCosanpa getInstancia(int imovelId) {
		if (instancia == null) {
		    instancia = new ImpressaoContaCosanpa();
		}
		return instancia;
    }

    
}
