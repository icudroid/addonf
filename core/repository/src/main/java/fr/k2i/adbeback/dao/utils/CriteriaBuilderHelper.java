package fr.k2i.adbeback.dao.utils;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 18/12/12
 * Time: 12:38
 * To change this template use File | Settings | File Templates.
 */
public class CriteriaBuilderHelper<T> {


    CriteriaBuilder criteria;
    Root<T> root;
    Predicate where;
    CriteriaQuery<T> query;

    EntityManager entityManager;

    Map<Attribute,Join> joins = new HashMap<Attribute, Join>();

    public RootHelper rootHelper = new RootHelper();
    public QueryHelper queryHelper = new QueryHelper();
    public CriteriaHelper criteriaHelper = new CriteriaHelper();


    public CriteriaBuilderHelper(EntityManager entityManager, Class<T> clazz) {
        this.entityManager = entityManager;
        criteria = entityManager.getCriteriaBuilder();
        query = criteria.createQuery(clazz);
        root = query.from(clazz);
    }

    /** Delegate Root methods */

    public class RootHelper{

//        public EntityType<T> getModel() {
//            return root.getModel();
//        }

//        public <Y> Path<Y> get(String attributeName) {
//            return root.get(attributeName);
//        }

        public Predicate isNull() {
            return root.isNull();
        }
        public Predicate in(Expression<Collection<?>> values) {
            return root.in(values);
        }
        public Predicate in(Object... values) {
            return root.in(values);
        }
        public Predicate isNotNull() {
            return root.isNotNull();
        }
        public Predicate in(Collection<?> values) {
            return root.in(values);
        }
        public Predicate in(Expression<?>... values) {
            return root.in(values);
        }



        public <K, V> MapJoin<T, K, V> join(MapAttribute<? super T, K, V> map, JoinType jt) {
            Join join = joins.get(map);
            if(join ==null){
                join =root.join(map, jt);
                joins.put(map,join);
            }
            return (MapJoin<T, K, V>) join;
        }
        public <Y> CollectionJoin<T, Y> join(CollectionAttribute<? super T, Y> collection) {
            Join join = joins.get(collection);
            if(join ==null){
                join =root.join(collection);
                joins.put(collection,join);
            }
            return (CollectionJoin<T, Y>) join;
        }
        public <Y> CollectionJoin<T, Y> join(CollectionAttribute<? super T, Y> collection, JoinType jt) {
            Join join = joins.get(collection);
            if(join ==null){
                join =root.join(collection,jt);
                joins.put(collection,join);
            }
            return (CollectionJoin<T, Y>) join;
        }
        public <Y> ListJoin<T, Y> join(ListAttribute<? super T, Y> list) {
            Join join = joins.get(list);
            if(join ==null){
                join =root.join(list);
                joins.put(list,join);
            }
            return (ListJoin<T, Y>) join;
        }
        public <K, V> MapJoin<T, K, V> join(MapAttribute<? super T, K, V> map) {
            Join join = joins.get(map);
            if(join ==null){
                join =root.join(map);
                joins.put(map,join);
            }
            return (MapJoin<T, K, V>) join;
        }
        public <Y> SetJoin<T, Y> join(SetAttribute<? super T, Y> set) {
            Join join = joins.get(set);
            if(join ==null){
                join =root.join(set);
                joins.put(set,join);
            }
            return (SetJoin<T, Y>) join;
        }
        public <Y> Join<T, Y> join(SingularAttribute<? super T, Y> attribute){
            Join join = joins.get(attribute);
            if(join ==null){
                join =root.join(attribute);
                joins.put(attribute,join);
            }
            return join;
        }
        public <Y> Join<T, Y> join(SingularAttribute<? super T, Y> attribute, JoinType jt) {
            Join join = joins.get(attribute);
            if(join ==null){
                join =root.join(attribute,jt);
                joins.put(attribute,join);
            }
            return join;
        }
        public <Y> SetJoin<T, Y> join(SetAttribute<? super T, Y> set, JoinType jt) {
            Join join = joins.get(set);
            if(join ==null){
                join =root.join(set,jt);
                joins.put(set,join);
            }
            return (SetJoin<T, Y>) join;
        }
        public <Y> ListJoin<T, Y> join(ListAttribute<? super T, Y> list, JoinType jt) {
            Join join = joins.get(list);
            if(join ==null){
                join =root.join(list,jt);
                joins.put(list,join);
            }
            return (ListJoin<T, Y>) join;
        }




        public Set<Fetch<T, ?>> getFetches() {
            return root.getFetches();
        }

        public From<T, T> getCorrelationParent() {
            return root.getCorrelationParent();
        }


        public Path<?> getParentPath() {
            return root.getParentPath();
        }








        public <X> Expression<X> as(Class<X> type) {
            return root.as(type);
        }



        public boolean isCorrelated() {
            return root.isCorrelated();
        }



        public <Y> Fetch<T, Y> fetch(PluralAttribute<? super T, ?, Y> attribute, JoinType jt) {
            return root.fetch(attribute, jt);
        }

        public Class<? extends T> getJavaType() {
            return root.getJavaType();
        }


        public Set<Join<T, ?>> getJoins() {
            return root.getJoins();
        }

        public Expression<Class<? extends T>> type() {
            return root.type();
        }

        public Selection<T> alias(String name) {
            return root.alias(name);
        }



        public String getAlias() {
            return root.getAlias();
        }



        public <Y> Fetch<T, Y> fetch(SingularAttribute<? super T, Y> attribute, JoinType jt) {
            return root.fetch(attribute, jt);
        }



        public <Y> Fetch<T, Y> fetch(PluralAttribute<? super T, ?, Y> attribute) {
            return root.fetch(attribute);
        }

        public boolean isCompoundSelection() {
            return root.isCompoundSelection();
        }



        public <E, C extends Collection<E>> Expression<C> get(PluralAttribute<T, C, E> collection) {
            return root.get(collection);
        }




        public <K, V, M extends Map<K,V>> Expression<M> get(MapAttribute<T, K, V> map) {
            return root.get(map);
        }

        public <Y> Path<Y> get(SingularAttribute<? super T, Y> attribute) {
            return root.get(attribute);
        }

        public List<Selection<?>> getCompoundSelectionItems() {
            return root.getCompoundSelectionItems();
        }


        public <Y> Fetch<T, Y> fetch(SingularAttribute<? super T, Y> attribute) {
            return root.fetch(attribute);
        }


    }




    /** Delegate Criteria */
    public class CriteriaHelper{

        private CriteriaBuilderAddHelper criteriaAddHelp(Predicate predicate) {
            if(where == null){
                where = predicate;
            }else{
                where.getExpressions().add(predicate);
            }
            return new CriteriaBuilderAddHelper(predicate);
        }

        public <Y> Expression<Y> all(Subquery<Y> subquery) {
            return criteria.all(subquery);
        }

        public Predicate isNull(Expression<?> x) {
            return criteria.isNull(x);
        }

        public Predicate isNotNull(Expression<?> x) {
            return criteria.isNotNull(x);
        }

        public <C extends Collection<?>> Expression<Integer> size(C collection) {
            return criteria.size(collection);
        }

        public Expression<String> substring(Expression<String> x, Expression<Integer> from, Expression<Integer> len) {
            return criteria.substring(x, from, len);
        }

        public Expression<Number> quot(Expression<? extends Number> x, Number y) {
            return criteria.quot(x, y);
        }

        public Expression<Date> currentDate() {
            return criteria.currentDate();
        }

        public <N extends Number> Expression<N> sum(Expression<? extends N> x, Expression<? extends N> y) {
            return criteria.sum(x, y);
        }

        public Predicate notLike(Expression<String> x, Expression<String> pattern, Expression<Character> escapeChar) {
            return criteria.notLike(x, pattern, escapeChar);
        }

        public Expression<Double> sqrt(Expression<? extends Number> x) {
            return criteria.sqrt(x);
        }

        public Predicate like(Expression<String> x, Expression<String> pattern, char escapeChar) {
            return criteria.like(x, pattern, escapeChar);
        }

        public Expression<String> trim(CriteriaBuilder.Trimspec ts, char t, Expression<String> x) {
            return criteria.trim(ts, t, x);
        }

        public <N extends Number> Expression<N> sum(N x, Expression<? extends N> y) {
            return criteria.sum(x, y);
        }

        public Predicate like(Expression<String> x, String pattern, char escapeChar) {
            return criteria.like(x, pattern, escapeChar);
        }

        public Expression<BigInteger> toBigInteger(Expression<? extends Number> number) {
            return criteria.toBigInteger(number);
        }

        public <Y> Expression<Y> some(Subquery<Y> subquery) {
            return criteria.some(subquery);
        }

        public <N extends Number> Expression<N> min(Expression<N> x) {
            return criteria.min(x);
        }

        public Predicate like(Expression<String> x, Expression<String> pattern, Expression<Character> escapeChar) {
            return criteria.like(x, pattern, escapeChar);
        }

        public Predicate notLike(Expression<String> x, String pattern, Expression<Character> escapeChar) {
            return criteria.notLike(x, pattern, escapeChar);
        }

        public <E, C extends Collection<E>> Predicate isNotMember(E elem, Expression<C> collection) {
            return criteria.isNotMember(elem, collection);
        }

        public Predicate lt(Expression<? extends Number> x, Number y) {
            return criteria.lt(x, y);
        }

        public <Y extends Comparable<? super Y>> Predicate greaterThanOrEqualTo(Expression<? extends Y> x, Y y) {
            return criteria.greaterThanOrEqualTo(x, y);
        }

        public Expression<Integer> toInteger(Expression<? extends Number> number) {
            return criteria.toInteger(number);
        }

        public Expression<Long> count(Expression<?> x) {
            return criteria.count(x);
        }

        public <V, M extends Map<?,V>> Expression<Collection<V>> values(M map) {
            return criteria.values(map);
        }

        public Order desc(Expression<?> x) {
            return criteria.desc(x);
        }

        public <Y extends Comparable<? super Y>> Predicate greaterThan(Expression<? extends Y> x, Expression<? extends Y> y) {
            return criteria.greaterThan(x, y);
        }

        public Expression<String> substring(Expression<String> x, int from) {
            return criteria.substring(x, from);
        }

        public <N extends Number> Expression<N> prod(N x, Expression<? extends N> y) {
            return criteria.prod(x, y);
        }

        public Expression<Double> toDouble(Expression<? extends Number> number) {
            return criteria.toDouble(number);
        }

        public <Y> Expression<Y> nullif(Expression<Y> x, Expression<?> y) {
            return criteria.nullif(x, y);
        }

        public Expression<String> trim(Expression<String> x) {
            return criteria.trim(x);
        }

        public <N extends Number> Expression<N> prod(Expression<? extends N> x, N y) {
            return criteria.prod(x, y);
        }

        public Predicate gt(Expression<? extends Number> x, Expression<? extends Number> y) {
            return criteria.gt(x, y);
        }

        public Expression<Integer> length(Expression<String> x) {
            return criteria.length(x);
        }

        public Predicate like(Expression<String> x, Expression<String> pattern) {
            return criteria.like(x, pattern);
        }

        public Expression<String> trim(Expression<Character> t, Expression<String> x) {
            return criteria.trim(t, x);
        }

        public <T> Expression<T> function(String name, Class<T> type, Expression<?>... args) {
            return criteria.function(name, type, args);
        }

        public Predicate isTrue(Expression<Boolean> x) {
            return criteria.isTrue(x);
        }

        public Expression<Integer> locate(Expression<String> x, String pattern, int from) {
            return criteria.locate(x, pattern, from);
        }

        public Expression<Float> toFloat(Expression<? extends Number> number) {
            return criteria.toFloat(number);
        }

        public Predicate le(Expression<? extends Number> x, Number y) {
            return criteria.le(x, y);
        }

        public Expression<Long> toLong(Expression<? extends Number> number) {
            return criteria.toLong(number);
        }

        public <Y extends Comparable<? super Y>> Predicate lessThan(Expression<? extends Y> x, Y y) {
            return criteria.lessThan(x, y);
        }

        public <N extends Number> Expression<N> sum(Expression<N> x) {
            return criteria.sum(x);
        }

        public Expression<String> toString(Expression<Character> character) {
            return criteria.toString(character);
        }

        public Predicate ge(Expression<? extends Number> x, Number y) {
            return criteria.ge(x, y);
        }

        public <N extends Number> Expression<N> diff(Expression<? extends N> x, N y) {
            return criteria.diff(x, y);
        }

        public Predicate notLike(Expression<String> x, String pattern) {
            return criteria.notLike(x, pattern);
        }

        public Predicate lt(Expression<? extends Number> x, Expression<? extends Number> y) {
            return criteria.lt(x, y);
        }

        public Expression<Integer> locate(Expression<String> x, Expression<String> pattern, Expression<Integer> from) {
            return criteria.locate(x, pattern, from);
        }

        public Expression<Integer> mod(Expression<Integer> x, Integer y) {
            return criteria.mod(x, y);
        }

        public Predicate equal(Expression<?> x, Object y) {
            return criteria.equal(x, y);
        }

        public CriteriaBuilderAddHelper and(Expression<Boolean> x, Expression<Boolean> y) {
            return criteriaAddHelp(criteria.and(x, y));
        }

        public CriteriaBuilderAddHelper and(Predicate... restrictions) {
            return criteriaAddHelp(criteria.and(restrictions));
        }
        public Predicate ge(Expression<? extends Number> x, Expression<? extends Number> y) {
            return criteria.ge(x, y);
        }

        public Expression<Number> quot(Number x, Expression<? extends Number> y) {
            return criteria.quot(x, y);
        }

        public Expression<Integer> mod(Integer x, Expression<Integer> y) {
            return criteria.mod(x, y);
        }

        public Expression<String> upper(Expression<String> x) {
            return criteria.upper(x);
        }

        public <R> CriteriaBuilder.Case<R> selectCase() {
            return criteria.selectCase();
        }

        public <E, C extends Collection<E>> Predicate isMember(E elem, Expression<C> collection) {
            return criteria.isMember(elem, collection);
        }

        public Predicate like(Expression<String> x, String pattern) {
            return criteria.like(x, pattern);
        }

        public <N extends Number> Expression<N> diff(N x, Expression<? extends N> y) {
            return criteria.diff(x, y);
        }

        public <C extends Collection<?>> Expression<Integer> size(Expression<C> collection) {
            return criteria.size(collection);
        }

        public <Y extends Comparable<? super Y>> Predicate greaterThanOrEqualTo(Expression<? extends Y> x, Expression<? extends Y> y) {
            return criteria.greaterThanOrEqualTo(x, y);
        }

        public <N extends Number> Expression<N> neg(Expression<N> x) {
            return criteria.neg(x);
        }

        public Predicate notLike(Expression<String> x, Expression<String> pattern) {
            return criteria.notLike(x, pattern);
        }

        public Predicate notLike(Expression<String> x, String pattern, char escapeChar) {
            return criteria.notLike(x, pattern, escapeChar);
        }

        public <T> Expression<T> literal(T value) {
            return criteria.literal(value);
        }

        public Expression<String> substring(Expression<String> x, int from, int len) {
            return criteria.substring(x, from, len);
        }

        public <T> ParameterExpression<T> parameter(Class<T> paramClass) {
            return criteria.parameter(paramClass);
        }

        public <N extends Number> Expression<N> sum(Expression<? extends N> x, N y) {
            return criteria.sum(x, y);
        }

        public <C, R> CriteriaBuilder.SimpleCase<C, R> selectCase(Expression<? extends C> expression) {
            return criteria.selectCase(expression);
        }

        public Expression<String> trim(CriteriaBuilder.Trimspec ts, Expression<Character> t, Expression<String> x) {
            return criteria.trim(ts, t, x);
        }

        public Expression<String> trim(char t, Expression<String> x) {
            return criteria.trim(t, x);
        }

        public <C extends Collection<?>> Predicate isNotEmpty(Expression<C> collection) {
            return criteria.isNotEmpty(collection);
        }

        public Predicate exists(Subquery<?> subquery) {
            return criteria.exists(subquery);
        }

        public Expression<String> lower(Expression<String> x) {
            return criteria.lower(x);
        }

        public <C extends Collection<?>> Predicate isEmpty(Expression<C> collection) {
            return criteria.isEmpty(collection);
        }

        public <N extends Number> Expression<N> abs(Expression<N> x) {
            return criteria.abs(x);
        }

        public <Y extends Comparable<? super Y>> Predicate lessThan(Expression<? extends Y> x, Expression<? extends Y> y) {
            return criteria.lessThan(x, y);
        }

        public Order asc(Expression<?> x) {
            return criteria.asc(x);
        }

        public Expression<Integer> mod(Expression<Integer> x, Expression<Integer> y) {
            return criteria.mod(x, y);
        }

        public Predicate le(Expression<? extends Number> x, Expression<? extends Number> y) {
            return criteria.le(x, y);
        }

        public <Y extends Comparable<? super Y>> Predicate greaterThan(Expression<? extends Y> x, Y y) {
            return criteria.greaterThan(x, y);
        }

        public Predicate notEqual(Expression<?> x, Object y) {
            return criteria.notEqual(x, y);
        }

        public <Y> Expression<Y> coalesce(Expression<? extends Y> x, Expression<? extends Y> y) {
            return criteria.coalesce(x, y);
        }

        public Predicate isFalse(Expression<Boolean> x) {
            return criteria.isFalse(x);
        }

        public <T> CriteriaBuilder.In<T> in(Expression<? extends T> expression) {
            return criteria.in(expression);
        }

        public Expression<Timestamp> currentTimestamp() {
            return criteria.currentTimestamp();
        }

        public Predicate disjunction() {
            return criteria.disjunction();
        }

        public Expression<Long> sumAsLong(Expression<Integer> x) {
            return criteria.sumAsLong(x);
        }

        public Expression<String> substring(Expression<String> x, Expression<Integer> from) {
            return criteria.substring(x, from);
        }

        public CompoundSelection<Tuple> tuple(Selection<?>... selections) {
            return criteria.tuple(selections);
        }

        public CriteriaBuilderAddHelper or(Predicate... restrictions) {
            return criteriaAddHelp(criteria.or(restrictions));
        }

        public <E, C extends Collection<E>> Predicate isMember(Expression<E> elem, Expression<C> collection) {
            return criteria.isMember(elem, collection);
        }

        public Predicate gt(Expression<? extends Number> x, Number y) {
            return criteria.gt(x, y);
        }

        public <Y extends Comparable<? super Y>> Predicate lessThanOrEqualTo(Expression<? extends Y> x, Expression<? extends Y> y) {
            return criteria.lessThanOrEqualTo(x, y);
        }

        public <Y extends Comparable<? super Y>> Predicate between(Expression<? extends Y> v, Y x, Y y) {
            return criteria.between(v, x, y);
        }

        public Expression<String> concat(String x, Expression<String> y) {
            return criteria.concat(x, y);
        }

        public CriteriaBuilderAddHelper or(Expression<Boolean> x, Expression<Boolean> y) {
            return criteriaAddHelp(criteria.or(x, y));
        }

        public Predicate notLike(Expression<String> x, Expression<String> pattern, char escapeChar) {
            return criteria.notLike(x, pattern, escapeChar);
        }

        public Expression<Integer> locate(Expression<String> x, Expression<String> pattern) {
            return criteria.locate(x, pattern);
        }

        public <N extends Number> Expression<N> diff(Expression<? extends N> x, Expression<? extends N> y) {
            return criteria.diff(x, y);
        }

        public <K, M extends Map<K,?>> Expression<Set<K>> keys(M map) {
            return criteria.keys(map);
        }

        public <T> CriteriaBuilder.Coalesce<T> coalesce() {
            return criteria.coalesce();
        }

        public <N extends Number> Expression<N> max(Expression<N> x) {
            return criteria.max(x);
        }

        public <N extends Number> Expression<Double> avg(Expression<N> x) {
            return criteria.avg(x);
        }

        public Predicate notEqual(Expression<?> x, Expression<?> y) {
            return criteria.notEqual(x, y);
        }

        public <X extends Comparable<? super X>> Expression<X> least(Expression<X> x) {
            return criteria.least(x);
        }

        public Expression<BigDecimal> toBigDecimal(Expression<? extends Number> number) {
            return criteria.toBigDecimal(number);
        }

        public <Y extends Comparable<? super Y>> Predicate lessThanOrEqualTo(Expression<? extends Y> x, Y y) {
            return criteria.lessThanOrEqualTo(x, y);
        }

        public Expression<String> concat(Expression<String> x, String y) {
            return criteria.concat(x, y);
        }

        public <T> Expression<T> nullLiteral(Class<T> resultClass) {
            return criteria.nullLiteral(resultClass);
        }

        public Predicate conjunction() {
            return criteria.conjunction();
        }

        public <T> CriteriaQuery<T> createQuery(Class<T> resultClass) {
            return criteria.createQuery(resultClass);
        }

        public Predicate not(Expression<Boolean> restriction) {
            return criteria.not(restriction);
        }

        public <N extends Number> Expression<N> prod(Expression<? extends N> x, Expression<? extends N> y) {
            return criteria.prod(x, y);
        }

        public Expression<String> trim(CriteriaBuilder.Trimspec ts, Expression<String> x) {
            return criteria.trim(ts, x);
        }

        public Predicate like(Expression<String> x, String pattern, Expression<Character> escapeChar) {
            return criteria.like(x, pattern, escapeChar);
        }

        public Expression<Integer> locate(Expression<String> x, String pattern) {
            return criteria.locate(x, pattern);
        }

        public <X extends Comparable<? super X>> Expression<X> greatest(Expression<X> x) {
            return criteria.greatest(x);
        }

        public <Y> Expression<Y> nullif(Expression<Y> x, Y y) {
            return criteria.nullif(x, y);
        }

        public <Y> Expression<Y> any(Subquery<Y> subquery) {
            return criteria.any(subquery);
        }

        public <E, C extends Collection<E>> Predicate isNotMember(Expression<E> elem, Expression<C> collection) {
            return criteria.isNotMember(elem, collection);
        }

        public Expression<Number> quot(Expression<? extends Number> x, Expression<? extends Number> y) {
            return criteria.quot(x, y);
        }

        public Expression<String> concat(Expression<String> x, Expression<String> y) {
            return criteria.concat(x, y);
        }

        public <Y extends Comparable<? super Y>> Predicate between(Expression<? extends Y> v, Expression<? extends Y> x, Expression<? extends Y> y) {
            return criteria.between(v, x, y);
        }

        public Expression<Time> currentTime() {
            return criteria.currentTime();
        }

        public CriteriaQuery<Tuple> createTupleQuery() {
            return criteria.createTupleQuery();
        }

        public Predicate equal(Expression<?> x, Expression<?> y) {
            return criteria.equal(x, y);
        }

        public CompoundSelection<Object[]> array(Selection<?>... selections) {
            return criteria.array(selections);
        }

        public <T> ParameterExpression<T> parameter(Class<T> paramClass, String name) {
            return criteria.parameter(paramClass, name);
        }

        public Expression<Double> sumAsDouble(Expression<Float> x) {
            return criteria.sumAsDouble(x);
        }

        public <Y> Expression<Y> coalesce(Expression<? extends Y> x, Y y) {
            return criteria.coalesce(x, y);
        }

        public Expression<Long> countDistinct(Expression<?> x) {
            return criteria.countDistinct(x);
        }

        public <Y> CompoundSelection<Y> construct(Class<Y> resultClass, Selection<?>... selections) {
            return criteria.construct(resultClass, selections);
        }

        public <X> Predicate ilike(SingularAttribute<? super T, String> x, String pattern) {
            return ilike(x, pattern, MatchMode.EXACT);
        }

        public <X> Predicate ilike(Path<String> x, String pattern, MatchMode matchMode) {
            return criteria.like(criteria.upper(x), matchMode.toMatchString(pattern.toUpperCase()));
        }

        public <X> Predicate ilike(SingularAttribute<? super T, String> x, String pattern, MatchMode matchMode) {
            return criteria.like(criteria.upper(root.get(x)), matchMode.toMatchString(pattern.toUpperCase()));
        }

        public <X> Predicate equal( SingularAttribute<? super T, X> x, Object y) {
            return criteria.equal(root.get(x), y);
        }

        public Predicate equal(Path<String> path, Object y) {
            return criteria.equal(path, y);
        }

    }



    /** Delegate query*/
    public class QueryHelper{

        public CriteriaQuery<T> select(Selection<? extends T> selection) {
            return query.select(selection);
        }

        public Predicate getRestriction() {
            return query.getRestriction();
        }

        public CriteriaQuery<T> multiselect(Selection<?>... selections) {
            return query.multiselect(selections);
        }

        public CriteriaQuery<T> multiselect(List<Selection<?>> selectionList) {
            return query.multiselect(selectionList);
        }

        public Predicate getGroupRestriction() {
            return query.getGroupRestriction();
        }

        public Selection<T> getSelection() {
            return query.getSelection();
        }

        public CriteriaQuery<T> having(Predicate... restrictions) {
            return query.having(restrictions);
        }

        public CriteriaQuery<T> orderBy(List<Order> o) {
            return query.orderBy(o);
        }

        public Set<ParameterExpression<?>> getParameters() {
            return query.getParameters();
        }

        public CriteriaQuery<T> groupBy(Expression<?>... grouping) {
            return query.groupBy(grouping);
        }

        public CriteriaQuery<T> distinct(boolean distinct) {
            return query.distinct(distinct);
        }

        public CriteriaQuery<T> groupBy(List<Expression<?>> grouping) {
            return query.groupBy(grouping);
        }

        public List<Order> getOrderList() {
            return query.getOrderList();
        }

        public CriteriaQuery<T> orderBy(Order... o) {
            return query.orderBy(o);
        }

        public Class<T> getResultType() {
            return query.getResultType();
        }

        public boolean isDistinct() {
            return query.isDistinct();
        }

        public <U> Subquery<U> subquery(Class<U> type) {
            return query.subquery(type);
        }

        public List<Expression<?>> getGroupList() {
            return query.getGroupList();
        }

        public CriteriaQuery<T> having(Expression<Boolean> restriction) {
            return query.having(restriction);
        }

        public Set<Root<?>> getRoots() {
            return query.getRoots();
        }

    }


    /**
     *
     * @return
     */
    public T getSingleResult() {
        if(where!=null){
            query.where(where);
        }
        try{
            return entityManager.createQuery(query).getSingleResult();
        }catch (javax.persistence.NoResultException e){
            return null;
        }

    }

    /**
     *
     * @return
     */
    public List<T> getResultList() {
        if(where!=null){
            query.where(where);
        }
        return entityManager.createQuery(query).getResultList();
    }


    /**
     *
     * @param startPosition
     * @param maxresult
     * @return
     */
    public List<T> getResultList(int startPosition,int maxresult) {
        if(where!=null){
            query.where(where);
        }
        TypedQuery<T> q = entityManager.createQuery(query);
        q.setFirstResult(startPosition);
        q.setMaxResults(maxresult);
        return q.getResultList();
    }


    /**
     *
     * @param x
     * @return
     */
    public Long count(Expression<?> x) {
        queryHelper.select((Selection<? extends T>) criteriaHelper.countDistinct(x));
        return (Long) getSingleResult();
    }

    /**
     *
     * @param attributName
     * @return
     */
    public Long count(String attributName) {
        queryHelper.select((Selection<? extends T>) criteriaHelper.countDistinct(root.get(attributName)));
        return (Long) getSingleResult();
    }

    /**
     *
     * @param id
     * @return
     */
    public Long count(SingularAttribute<? super T, String> id) {
        queryHelper.select((Selection<? extends T>) criteriaHelper.countDistinct(root.get(id)));
        return (Long) getSingleResult();
    }


    public class CriteriaBuilderAddHelper{
        private Predicate predicate;

        public CriteriaBuilderAddHelper(Predicate predicate) {
            this.predicate = predicate;
        }

        public CriteriaBuilderAddHelper add(Predicate predicate){
            this.predicate.getExpressions().add(predicate);
            return this;
        }
    }







    public enum MatchMode {

        /**
         * Match the entire string to the pattern
         */
        EXACT {
            public String toMatchString(String pattern) {
                return pattern;
            }
        },

        /**
         * Match the start of the string to the pattern
         */
        START {
            public String toMatchString(String pattern) {
                return pattern + '%';
            }
        },

        /**
         * Match the end of the string to the pattern
         */
        END {
            public String toMatchString(String pattern) {
                return '%' + pattern;
            }
        },

        /**
         * Match the pattern anywhere in the string
         */
        ANYWHERE {
            public String toMatchString(String pattern) {
                return '%' + pattern + '%';
            }
        };

        /**
         * convert the pattern, by appending/prepending "%"
         */
        public abstract String toMatchString(String pattern);

    }



}
