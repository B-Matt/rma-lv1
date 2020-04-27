package com.matejarlovic.famouspeople

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_person_list.*

class PersonListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        return inflater.inflate(R.layout.fragment_person_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Setup People List
        setDefaultPeople()

        // Apply Person List Adapter to the view
        personList.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = PersonListAdapter()
        }
    }

    private fun setDefaultPeople()
    {
        if(PeopleRepository.instance.length() != 0) {
            return
        }

        PeopleRepository.instance.add(
            InspiringPerson(
                "https://cdn.britannica.com/81/191581-050-8C0A8CD3/Alan-Turing.jpg",
                "Alan Turing",
                "23/06/1912",
                "07/06/1954",
                "Alan Mathison Turing OBE FRS was an English mathematician, computer scientist, logician, cryptanalyst, philosopher, and theoretical biologist.",
                listOf(
                    PersonQuote("Sometimes it is the people no one can imagine anything of who do the things no one can imagine."),
                    PersonQuote("Those who can imagine anything, can create the impossible."),
                    PersonQuote("If a machine is expected to be infallible, it cannot also be intelligent.")
                ) as MutableList<PersonQuote>
            )
        )

        PeopleRepository.instance.add(
            InspiringPerson(
                "http://themotivationalsociety.com/wp-content/uploads/2019/10/larry-page-1234-696x580.jpg",
                "Larry Page",
                "26/03/1973",
                "?",
                "Lawrence Edward Page is an American software engineer and Internet entrepreneur. He is best known as one of the co-founders of Google along with Sergey Brin.",
                listOf(
                    PersonQuote("You don’t need to have a 100 person company to develop that idea."),
                    PersonQuote("Always deliver more than expected."),
                    PersonQuote("Always work hard on something uncomfortably exciting.")
                ) as MutableList<PersonQuote>
            )
        )

        PeopleRepository.instance.add(
            InspiringPerson(
                "https://cdn.vox-cdn.com/thumbor/7czlznSPvi4ybwBCYrjppjyzTiA=/0x0:4338x3084/920x613/filters:focal(1868x911:2562x1605):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/52971561/103917783.0.jpg",
                "Sergey Brin",
                "21/08/1973",
                "?",
                "Sergey Mikhaylovich Brin is an American software engineer and Internet entrepreneur. Together with Larry Page, he co-founded Google.",
                listOf(
                    PersonQuote("Solving big problems is easier than solving little ones."),
                    PersonQuote("We will make machines that can reason, think and do things better than we can."),
                    PersonQuote("Too many rules stifle innovation.")
                ) as MutableList<PersonQuote>
            )
        )

        PeopleRepository.instance.add(
            InspiringPerson(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Ada_Lovelace_portrait.jpg/220px-Ada_Lovelace_portrait.jpg",
                "Ada Byron",
                "10/12/1815",
                "27/11/1852",
                "Ada Byron was an English mathematician and writer. She detected first bug in the computer.",
                listOf(
                    PersonQuote("If you can't give me poetry, can't you give me poetical science?"),
                    PersonQuote("That brain of mine is something more than merely mortal, as time will show."),
                    PersonQuote("I shall, in due time, be a Poet.")
                ) as MutableList<PersonQuote>
            )
        )

        PeopleRepository.instance.add(
            InspiringPerson(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dc/Steve_Jobs_Headshot_2010-CROP_%28cropped_2%29.jpg/619px-Steve_Jobs_Headshot_2010-CROP_%28cropped_2%29.jpg",
                "Steve Jobs",
                "24/02/1955",
                "05/10/2011",
                "Steve Jobs co-founded Apple Computers with Steve Wozniak. Under Jobs' guidance, the company pioneered a series of revolutionary technologies, including the iPhone and iPad.",
                listOf(
                    PersonQuote("Details matter, it’s worth waiting to get it right."),
                    PersonQuote("I’m actually as proud of many of the things we haven’t done as the things we have done."),
                    PersonQuote("If you don’t love it, you’re going to fail.")
                ) as MutableList<PersonQuote>
            )
        )

        PeopleRepository.instance.add(
            InspiringPerson(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Bill_Gates_June_2015.png/462px-Bill_Gates_June_2015.png",
                "Bill Gates",
                "28/10/1955",
                "?",
                "Bill Gates is an American business magnate, software developer, investor, and philanthropist. He is best known as the co-founder of Microsoft Corporation.",
                listOf(
                    PersonQuote("Don’t compare yourself with anyone in this world…if you do so, you are insulting yourself."),
                    PersonQuote("Life is not fair get, used to it!"),
                    PersonQuote("I choose a lazy person to do a hard job. Because a lazy person will find an easy way to do it.")
                ) as MutableList<PersonQuote>
            )
        )
    }
}
