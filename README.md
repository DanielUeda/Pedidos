# Pedidos

API desenvolvida utilizando JAVA 15 com Spring Boot, sendo disponibilizada na porta 8996
Conexão com o banco PostgreSQL através da porta 5432, na primeira execução do projeto deverá ser criado um banco de dados com o nome "pedidosteste" previamente, o projeto através do JPA se encarregará de criar as entidades.
O usuario e senha do banco PostgreSQL deverá ser postgres.

Não utilizei QueryDSL por nunca ter tido contato.

Para a inserção de um produto deverá ser utilizada a seguinte URI, sendo um POST, localhost:8996/produto
Para a inserção de um pedido deverá ser utilizada a seguinte URI, sendo um POST, localhost:8996/pedido
Para a inserção de um item do pedido o item deverá ser inserido a partir da URI, sendo um POST, localhost:8996/pedido-item e posteriormente vinculado a um pedido através da URI localhost:8996/{pedidoId}/item/{item}/add
Para remover um item do pedido a URI será um POST: localhost:8996/{pedidoId}/item/{item}/delete
Para a aplicação do desconto de um pedido deverá ser utilizada a seguinte URI com POST: localhost:8996/{pedidoId}/desconto

Para todas as entidades(Produto, Pedido e PedidoItem) existem as funções de PUT, DELETE seguindo o mesmo padrão de URI: localhost:8996/{entidade}/{id}
Para Pedido e Produto, existe o endpoint /search, que é uma busca paginada por padrão retornando a primeira pagina e os primeiros 10 itens, podendo ser filtrada por descrição.

Foram criados dois Enumerator, um para o tipo do produto, sendo SERVICO ou PRODUTO e outro para a situação do pedido, sendo ABERTO ou FECHADO.
