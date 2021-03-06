package web.servlet;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BeanFactory {
    //就给出一个接口类型
    //返回具体实现类对象
    public static <T>T newInstance(Class<T> interfaceClass)  {
        //反射api 获取类的名字
        //获取接口名字
        String name = interfaceClass.getSimpleName();
        //解析xml 目的为根据接口名字获取到 对应实现类的全限定名

        SAXReader saxReader = new SAXReader();
        //获取beans.xml的流
        try {
            Document document = saxReader.read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
            //xpath语法找到对应的元素
            //根据name属性获取 某一个元素  现在就知道可以找到 那个元素
            Element element = (Element) document.selectSingleNode("//bean[@name='" + name + "']");
            //获取子类全限定名
            String subClass = element.attributeValue("class");

            //反射获取子类对象
            Class<?> aClass = Class.forName(subClass);
            
            //反射创建对象 今天先不要管泛型
            return (T)aClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
