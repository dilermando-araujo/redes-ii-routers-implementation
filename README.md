# redes-ii-arp-implementation
O objetivo dessa aplicação é implementar em algo nível o protocolo ARP, utilizado pelos dispositivos de rede para encontrar um MAC a partir de um IP. O funcionamento desse protocolo é descrito no diagrama abaixo:

![Diagrama de sequência do protocolo ARP](docs/sequence-diagram.png)

## Como executar
Algumas formas de executar essa aplicação são:

1. Na IDE de preferência, execute a classe principal (app.Application);
2. Com o Maven instalado, execute na raiz do projeto o comando **mvn exec:java**.