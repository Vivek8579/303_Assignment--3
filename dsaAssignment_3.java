import java.util.*;

class B {
    int i;
    String n;

    B(int i, String n) {
        this.i = i;
        this.n = n;
    }

    public String toString() {
        return i + "-" + n;
    }
}

class N {
    B b;
    N l, r;

    N(B b) {
        this.b = b;
    }
}

class T {
    N r;

    N f(N n, B b) {
        if (n == null)
            return new N(b);
        if (b.i < n.b.i)
            n.l = f(n.l, b);
        else if (b.i > n.b.i)
            n.r = f(n.r, b);
        else
            n.b = b;
        return n;
    }

    void ins(B b) {
        r = f(r, b);
    }

    void in(N n, List<B> a) {
        if (n == null)
            return;
        in(n.l, a);
        a.add(n.b);
        in(n.r, a);
    }
}

class A {
    B b;
    A l, r;
    int h = 1;

    A(B b) {
        this.b = b;
    }
}

class AV {
    A r;

    int h(A x) {
        return x == null ? 0 : x.h;
    }

    void u(A x) {
        x.h = 1 + Math.max(h(x.l), h(x.r));
    }

    A L(A y) {
        A x = y.l, t = x.r;
        x.r = y;
        y.l = t;
        u(y);
        u(x);
        return x;
    }

    A R(A x) {
        A y = x.r, t = y.l;
        y.l = x;
        x.r = t;
        u(x);
        u(y);
        return y;
    }

    A f(A n, B b) {
        if (n == null)
            return new A(b);
        if (b.i < n.b.i)
            n.l = f(n.l, b);
        else if (b.i > n.b.i)
            n.r = f(n.r, b);
        else
            return n;
        u(n);
        int k = h(n.l) - h(n.r);
        if (k > 1 && b.i < n.l.b.i)
            return L(n);
        if (k < -1 && b.i > n.r.b.i)
            return R(n);
        if (k > 1) {
            n.l = R(n.l);
            return L(n);
        }
        if (k < -1) {
            n.r = L(n.r);
            return R(n);
        }
        return n;
    }

    void ins(B b) {
        r = f(r, b);
    }

    void in(A n, List<B> a) {
        if (n == null)
            return;
        in(n.l, a);
        a.add(n.b);
        in(n.r, a);
    }
}

class G {
    Map<Integer, List<int[]>> m = new HashMap<>();

    void a(int u, int v, int w) {
        m.computeIfAbsent(u, x -> new ArrayList<>()).add(new int[] { v, w });
        m.computeIfAbsent(v, x -> new ArrayList<>()).add(new int[] { u, w });
    }

    int[] bfs(int s) {
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        Set<Integer> v = new HashSet<>();
        v.add(s);
        List<Integer> o = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            o.add(u);
            for (int[] p : m.get(u))
                if (v.add(p[0]))
                    q.add(p[0]);
        }
        return o.stream().mapToInt(i -> i).toArray();
    }

    int[] dfs(int s) {
        Set<Integer> v = new HashSet<>();
        List<Integer> o = new ArrayList<>();
        d(s, v, o);
        return o.stream().mapToInt(i -> i).toArray();
    }

    void d(int u, Set<Integer> v, List<Integer> o) {
        v.add(u);
        o.add(u);
        for (int[] p : m.get(u))
            if (!v.contains(p[0]))
                d(p[0], v, o);
    }

    Map<Integer, Integer> dij(int s) {
        Map<Integer, Integer> d = new HashMap<>();
        for (int x : m.keySet())
            d.put(x, 999999);
        d.put(s, 0);
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(x -> x[1]));
        pq.add(new int[] { s, 0 });
        while (!pq.isEmpty()) {
            int[] c = pq.poll();
            int u = c[0], du = c[1];
            if (du != d.get(u))
                continue;
            for (int[] p : m.get(u)) {
                int v = p[0], w = p[1];
                if (du + w < d.get(v)) {
                    d.put(v, du + w);
                    pq.add(new int[] { v, du + w });
                }
            }
        }
        return d;
    }
}

class U {
    Map<Integer, Integer> p = new HashMap<>(), r = new HashMap<>();

    U(Set<Integer> s) {
        for (int x : s) {
            p.put(x, x);
            r.put(x, 0);
        }
    }

    int f(int x) {
        if (p.get(x) != x)
            p.put(x, f(p.get(x)));
        return p.get(x);
    }

    boolean u(int a, int b) {
        int x = f(a), y = f(b);
        if (x == y)
            return false;
        if (r.get(x) < r.get(y))
            p.put(x, y);
        else {
            p.put(y, x);
            if (r.get(x) == r.get(y))
                r.put(x, r.get(x) + 1);
        }
        return true;
    }
}

class E {
    String v;
    E l, r;

    E(String v) {
        this.v = v;
    }
}

public class dsaAssignment_3 {
    static List<String> P(String s) {
        Map<String, Integer> p = new HashMap<>();
        p.put("+", 1);
        p.put("-", 1);
        p.put("*", 2);
        p.put("/", 2);
        List<String> a = new ArrayList<>();
        Stack<String> st = new Stack<>();
        for (int i = 0; i < s.length();) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.'))
                    sb.append(s.charAt(i++));
                a.add(sb.toString());
                continue;
            }
            if (c == '(') {
                st.push("(");
                i++;
                continue;
            }
            if (c == ')') {
                i++;
                while (!st.peek().equals("("))
                    a.add(st.pop());
                st.pop();
                continue;
            }
            String o = c + "";
            while (!st.isEmpty() && !st.peek().equals("(") && p.get(st.peek()) >= p.get(o))
                a.add(st.pop());
            st.push(o);
            i++;
        }
        while (!st.isEmpty())
            a.add(st.pop());
        return a;
    }

    static E B(List<String> a) {
        Stack<E> s = new Stack<>();
        for (String x : a) {
            if (x.matches("[0-9.]+"))
                s.push(new E(x));
            else {
                E r = s.pop(), l = s.pop(), n = new E(x);
                n.l = l;
                n.r = r;
                s.push(n);
            }
        }
        return s.pop();
    }

    static double V(E n) {
        if (n.v.matches("[0-9.]+"))
            return Double.parseDouble(n.v);
        double A = V(n.l), B = V(n.r);
        return n.v.equals("+") ? A + B : n.v.equals("-") ? A - B : n.v.equals("*") ? A * B : A / B;
    }

    public static void main(String[] z) {
        B[] x = { new B(10, "A"), new B(5, "B"), new B(20, "C"), new B(15, "D"), new B(2, "E") };
        T t = new T();
        for (B y : x)
            t.ins(y);
        List<B> a = new ArrayList<>();
        t.in(t.r, a);
        System.out.println(a);
        AV av = new AV();
        for (B y : x)
            av.ins(y);
        List<B> a2 = new ArrayList<>();
        av.in(av.r, a2);
        System.out.println(a2);
        G g = new G();
        g.a(10, 5, 3);
        g.a(10, 20, 5);
        g.a(5, 2, 4);
        g.a(20, 15, 2);
        g.a(15, 5, 6);
        System.out.println(Arrays.toString(g.bfs(10)));
        System.out.println(Arrays.toString(g.dfs(10)));
        System.out.println(g.dij(10));
        Set<Integer> s = g.m.keySet();
        List<int[]> ed = new ArrayList<>();
        for (int u : s)
            for (int[] p : g.m.get(u))
                if (u <= p[0])
                    ed.add(new int[] { p[1], u, p[0] });
        ed.sort(Comparator.comparingInt(o -> o[0]));
        U uf = new U(s);
        int W = 0;
        for (int[] e : ed)
            if (uf.u(e[1], e[2]))
                W += e[0];
        System.out.println(W);
        System.out.println(V(B(P("100+(50*2)-10/2"))));
    }
}
