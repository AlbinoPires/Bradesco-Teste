
# Simulação de Versionamento API

Este projeto demonstra a utilização de Spring Boot com versionamento de API dentro do mesmo controller, aplicando boas práticas de arquitetura e técnicas avançadas de injeção de dependência.

🔑 Principais pontos:

- Versionamento de API: Implementação de múltiplas versões (v1, v2, etc.) em um único controller, garantindo compatibilidade retroativa e evolução contínua da aplicação sem quebra para clientes existentes.

- Uso de @Component e @Qualifier: Estratégia avançada para gerenciar diferentes implementações de serviços, permitindo que cada versão da API utilize sua própria lógica de negócio de forma desacoplada.

- Organização e escalabilidade: Estrutura pensada para facilitar manutenção e expansão, evitando duplicação de código e mantendo clareza na evolução das versões.

- Boas práticas REST: Endpoints bem definidos, seguindo convenções RESTful e mantendo consistência entre versões.

🚀 Benefícios da abordagem:

- Redução de complexidade ao centralizar o versionamento em um único controller.

- Flexibilidade para evoluir regras de negócio sem impactar versões anteriores.

- Facilita testes e documentação, já que todas as versões estão organizadas de forma coesa.

🔑 Fato Importante:

- Não tem relação com nenhuma empresa, é uma simples implementação sobre as apis do mercado que tem que se atualizar sobre a nova diretriz de cnpj alfanumerico, isso traz diversas formas, uma delas para manter a api funcional, é implementar um versionamento com injeção de dependência inteligente e dinâmico, essa Simulação simples foi feita, sem nenhuma regra específica.

- Fato se dá a título de teste e aprendizado contínuo. Experiência muito gratificante pois se colocar as orientações abaixo seu teste funcionará e chegará ao propósito final:  
    - V1 funcional como original;
    - V2 funcional como nova implementação;
    - ambas funcionam ao mesmo tempo, a manutenção do código fica visualmente fácil e prática para novas implementações;
    - No interior do projeto deixarei um arquivo com os dados testados, livre para qualquer teste pessoal sem finalidade lucrativa. 




