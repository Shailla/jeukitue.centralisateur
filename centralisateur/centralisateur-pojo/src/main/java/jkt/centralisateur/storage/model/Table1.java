package jkt.centralisateur.storage.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "table1")
public class Table1 implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Set<Table2> tables2;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TA1_ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy="table1")
    public Set<Table2> getTables2() {
        return tables2;
    }
    
    public void setTables2(final Set<Table2> tables2) {
        this.tables2 = tables2;
    }
}

