package jkt.centralisateur.storage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "table2")
public class Table2 implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Table1 table1;
    private String hashcode;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TA2_ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="TA1_ID", nullable=false)
    public Table1 getTable1() {
        return table1;
    }

    public void setTable1(Table1 table1) {
        this.table1 = table1;
    }    
    
    @Column(name="TA2_HASHCODE", nullable=true)
    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }
}

