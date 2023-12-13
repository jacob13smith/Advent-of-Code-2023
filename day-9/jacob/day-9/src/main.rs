use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;

fn main() {
    if let Ok(lines) = read_lines("./input.txt") {
        let mut histories: Vec<Vec<i64>> = vec![];
        for line in lines {
            if let Ok(line_str) = line {
                let history: Vec<i64> = line_str.split(" ").map(|x| x.parse::<i64>().unwrap()).collect();
                histories.push(history);
            }
        }
        
        let mut sum = 0;
        for history in histories {
            let mut i = history.len() - 1;
            let mut rows: Vec<Vec<i64>> = vec![];
            let mut curr = history[i] - history[i-1];
            let mut depth = 1;
            while curr != 0 
            {
                if rows.len() < depth {
                    rows.push(vec![]);
                }
                let curr_row = rows.last_mut().unwrap();
                curr_row.push(curr);


                if curr_row.len() > 1 {
                    // Handle when we can caluclate another neighbour
                    curr = curr_row[curr_row.len() - 1] - curr;
                    depth += 1;
                } else {
                    i -= 1;
                    curr = history[i] - history[i-1];
                }

            }

            for row in rows {
                for item in row {
                    print!("{} ", item);
                }
                println!();
            }

            let mut curr_sum = 0;
            // for idx in rows.len()-1..=0 {
            //     curr_sum += rows[idx].first().unwrap();
            // }

            //println!("{}", history[history.len() - 1] + curr_sum);
        }

    }
}

// The output is wrapped in a Result to allow matching on errors
// Returns an Iterator to the Reader of the lines of the file.
fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}
