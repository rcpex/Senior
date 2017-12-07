package Senior.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import Senior.models.Cidade;

public interface CidadeRepository extends CrudRepository<Cidade, String> {
	
	Cidade save(Cidade cidade);
	
	void delete(Cidade cidade);
	
	List<Cidade> findAll();
	
	List<Cidade> findByUf(String uf);
	
	Cidade findByIbgeId(String ibgeId);
	
	@Query("SELECT c FROM Cidade c WHERE c.capital is true ORDER BY c.name")
	List<Cidade> getCapitaisOrdenadasPorNome();
	
	@Query("SELECT c.uf, COUNT(c) FROM Cidade c GROUP BY c.uf")
	List<Object[]> getQtdeCidadesPorEstado();
	
	@Query("SELECT c FROM Cidade c WHERE :coluna = :filtro")
	List<Cidade> getCidadesPorFiltro(@Param("coluna") String coluna, @Param("filtro") String filtro);
	
	@Query("SELECT COUNT(c) FROM Cidade c group by :coluna")
	Integer getQtdeRegistrosPorColuna(@Param("coluna") String coluna);
	
	@Query("SELECT COUNT(c) FROM Cidade c")
	int getQtdeRegistros();
}
