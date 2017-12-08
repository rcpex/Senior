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

	@Query("SELECT c FROM Cidade c WHERE c.ibgeId LIKE %:filtro%")
	List<Cidade> getCidadesPorIbgeId(@Param("filtro") String filtro);
	
	@Query("SELECT c FROM Cidade c WHERE c.uf LIKE %:filtro%")
	List<Cidade> getCidadesPorEstado(@Param("filtro") String filtro);
	
	@Query("SELECT c FROM Cidade c WHERE c.name LIKE %:filtro%")
	List<Cidade> getCidadesPorName(@Param("filtro") String filtro);
	
	@Query("SELECT c FROM Cidade c WHERE c.noAccents LIKE %:filtro%")
	List<Cidade> getCidadesPorNoAccents(@Param("filtro") String filtro);
	
	@Query("SELECT c FROM Cidade c WHERE c.alternativeNames LIKE %:filtro%")
	List<Cidade> getCidadesPorAlternativeNames(@Param("filtro") String filtro);
	
	@Query("SELECT c FROM Cidade c WHERE c.microregion LIKE %:filtro%")
	List<Cidade> getCidadesPorMicroregion(@Param("filtro") String filtro);
	
	@Query("SELECT c FROM Cidade c WHERE c.mesoregion LIKE %:filtro%")
	List<Cidade> getCidadesPorMesoregion(@Param("filtro") String filtro);
	
	@Query("SELECT c.uf, COUNT(c) FROM Cidade c GROUP BY c.uf")
	List<Object[]> getQtdeCidadesPorEstado();
	
	@Query("SELECT c.microregion, COUNT(c) FROM Cidade c GROUP BY c.microregion")
	List<Object[]> getQtdeCidadesPorMicroregion();
	
	@Query("SELECT c.mesoregion, COUNT(c) FROM Cidade c GROUP BY c.mesoregion")
	List<Object[]> getQtdeCidadesPorMesoregion();
	
	@Query("SELECT c.name, COUNT(c) FROM Cidade c GROUP BY c.name")
	List<Object[]> getQtdeCidadesPorName();
	
	@Query("SELECT COUNT(c) FROM Cidade c")
	int getQtdeRegistros();
}
